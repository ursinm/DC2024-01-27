export const transformResult = (result: any) => {
    let convertedObject: { [key: string]: number } = {};
    Object.keys(result)
        .filter((key) => key.endsWith('Id') || key.toLowerCase() === 'id')
        .forEach((key) => {
            convertedObject[key] = +result[key];
        });
    return {
        ...result,
        ...convertedObject,
    };
};

export const transformResults = (result: any[]) => {
    return result.map((row) => transformResult(row));
};
