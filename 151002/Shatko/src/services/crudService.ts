import { schema, client } from '../db/init';
import { GetQueryParams } from '../types';
import { transformResult, transformResults } from '../utils/transformResult';

class CrudService {
    getAll = async <T extends object>(table: string, query: GetQueryParams): Promise<T[]> => {
        const { filter, sort } = query;
        const limit = query.limit ? +query.limit : 0;
        const offset = query.offset ? +query.offset : 0;

        return transformResults((await client.query(`SELECT * FROM ${schema}.${table}`)).rows);
    };

    getByPk = async <T extends object>(id: string, table: string): Promise<T> => {
        return transformResult(
            (await client.query(`SELECT * FROM ${schema}.${table} WHERE id = ${id}`)).rows[0],
        );
    };

    create = async <T extends object>(body: Omit<T, 'id'>, table: string): Promise<T> => {
        const columns = this.getKeys(body);
        const values = this.getValues(body);

        const result = await client.query(
            `INSERT INTO ${schema}.${table} 
                (${columns}) VALUES (${values})
                RETURNING *`,
        );

        return transformResult(result.rows[0]);
    };

    delete = async (id: string, table: string): Promise<void> => {
        await client.query(`DELETE FROM ${schema}.${table} WHERE id = ${id}`);
    };

    update = async <T extends { id: number }>(
        body: T,
        table: string,
        modified?: boolean,
    ): Promise<T> => {
        const setters = this.getEntries(body);

        const result = await client.query(`
                UPDATE ${schema}.${table} 
                SET ${setters}
                ${modified ? `, modified = '${new Date().toISOString()}'` : ''}
                WHERE id = ${body.id}
                RETURNING *`);

        return transformResult(result.rows[0]);
    };

    isEntityPresentByPk = async (id: string | number, table: string): Promise<Boolean> => {
        return !!(
            await client.query(
                `SELECT * FROM ${schema}.${table} WHERE id = ${this.getWrappedValue(id)}`,
            )
        ).rows.length;
    };

    isEntityPresent = async (
        attribute: string,
        value: string | number,
        table: string,
    ): Promise<Boolean> => {
        return !!(
            await client.query(
                `SELECT * FROM ${schema}.${table} WHERE "${attribute}" = ${this.getWrappedValue(value)}`,
            )
        ).rows.length;
    };

    private getWrappedValue = (value: string | number) => {
        if (typeof value === 'string') {
            return `'${value}'`;
        }

        return value;
    };

    private getKeys = <T extends object>(body: T) => {
        let keys = '';

        Object.keys(body).forEach((key, index, array) => {
            if (index === array.length - 1) {
                keys += `"${key}"`;
                return;
            }
            keys += `"${key}", `;
        });

        return keys;
    };

    private getValues = <T extends object>(body: T) => {
        let values = '';

        Object.values(body).forEach((value, index, array) => {
            if (index === array.length - 1) {
                if (typeof value === 'number') {
                    values += `${value}`;
                    return;
                }
                values += `'${value}'`;
                return;
            }
            if (typeof value === 'number') {
                values += `${value}, `;
                return;
            }
            values += `'${value}', `;
        });

        return values;
    };

    private getEntries = <T extends object>(body: T) => {
        let entries = '';

        Object.entries(body).forEach(([key, value], index, array) => {
            if (index === array.length - 1) {
                if (typeof value === 'number') {
                    entries += `"${key}" = ${value}`;
                    return;
                }
                entries += `"${key}" = '${value}'`;
                return;
            }
            if (typeof value === 'number') {
                entries += `"${key}" = ${value}, `;
                return;
            }
            entries += `"${key}" = '${value}', `;
        });

        return entries;
    };
}

export default new CrudService();
