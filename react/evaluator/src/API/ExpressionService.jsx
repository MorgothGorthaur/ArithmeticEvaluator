export default class ExpressionService {
    static async getAll() {
        try {
            const response = await fetch('http://localhost:8080/task/all');
            return await response.json();
        } catch (e) {
            alert(e);
        }
    };

    static async addExpression(arithmeticExpression) {
        try {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({arithmeticExpression: arithmeticExpression})
            };
            const response = await fetch('http://localhost:8080/task', requestOptions);
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }
};