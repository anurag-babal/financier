import axios from 'axios';

const apiUrl = process.env.API_URL;

const transactions = [];

export async function getTransactions() {
    // const response = axios.get(`${apiUrl}/transactions`);
    await new Promise(resolve => setTimeout(resolve, 1000));
    return transactions;
}

export async function getTransaction(id) {
    // const response = axios.get(`${apiUrl}/transactions/${id}`);
    return transactions.find(transaction => transaction.id === id);
}

export async function addTransaction(transaction) {
    try {
        await new Promise(resolve => setTimeout(resolve, 1000));
        // const response = await axios.post(`${apiUrl}/transactions`, transaction);
        if (!transaction.id) {
            transaction.id = transactions.length + 1;
        }
        transactions.push(transaction);
        return "Expense added successfully";
    } catch (error) {
        throw error;
    }
}

export async function deleteTransaction(id) {
    // const response = axios.delete(`${apiUrl}/transactions/${id}`);
    const index = transactions.findIndex(transaction => transaction.id === id);
    if (index > -1) {
        transactions.splice(index, 1);
    }
    return "Expense deleted successfully";
}