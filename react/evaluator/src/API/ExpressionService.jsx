export default class ExpressionService {
    static async getAll() {
        try {
            const response = await fetch('http://localhost:8080/task/all');
            return await response.json();
        } catch (e) {
            alert(e);
        }
    };
    static async getByResult(res) {
        try {
            const response = await fetch('http://localhost:8080/task/all/' + res);
            return await response.json();
        } catch (e) {
            alert(e);
        }
    };
    static async getWithLowerResults(res) {
        try {
            const response = await fetch('http://localhost:8080/task/all/lower/' + res);
            return await response.json();
        } catch (e) {
            alert(e);
        }
    };
    static async getWithHigherResults(res) {
        try {
            const response = await fetch('http://localhost:8080/task/all/higher/' + res);
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

    static async changeExpression(id, arithmeticExpression) {
        try {
            const requestOptions = {
                method: 'PATCH',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({id : id, arithmeticExpression: arithmeticExpression})
            };
            const response = await fetch('http://localhost:8080/task', requestOptions);
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }
};