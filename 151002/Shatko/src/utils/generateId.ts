export const generateId = () => {
    const length = 15;
    let id = '';

    for (let i = 0; i < length; i++) {
        id += Math.floor(Math.random() * 10).toString();
    }

    return Number(id);
};
