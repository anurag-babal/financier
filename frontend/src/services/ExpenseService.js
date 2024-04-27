import {Expense} from "../model/Expense";

const apiUrl = process.env.API_URL;

const expenses = [
    new Expense(1, "Grocery", 500, "Bought groceries", "2024-01-01"),
    new Expense(2, "Transport", 200, "Took a cab", "2024-01-02"),
    new Expense(3, "Food", 300, "Had lunch", "2024-01-03"),
    new Expense(4, "Grocery", 400, "Bought groceries", "2024-01-04"),
    new Expense(5, "Transport", 100, "Took a cab", "2024-01-05"),
    new Expense(6, "Food", 200, "Had lunch", "2024-04-06"),
    new Expense(7, "Grocery", 300, "Bought groceries", "2024-04-07")
];

export async function getExpenses() {
    try {
        // const response = axios.get(`${apiUrl}/transactions`);
        await new Promise(resolve => setTimeout(resolve, 1000));
        return expenses;
    } catch (error) {
        console.error('Error while fetching expenses', error);
        throw error;
    }
}

export async function getExpense(id) {
    try {
        // const response = axios.get(`${apiUrl}/transactions/${id}`);
        await new Promise(resolve => setTimeout(resolve, 1000));
        return expenses.find(expense => expense.id === id);
    } catch (error) {
        console.error('Error while fetching expense', error);
        throw error;
    }
}

export async function saveExpense(expense) {
    try {
        // const response = await axios.post(`${apiUrl}/transactions`, transaction);
        await new Promise(resolve => setTimeout(resolve, 1000));
        const index = expenses.findIndex(e => e.id === expense.id);
        if (index > -1) {
            expenses[index] = expense;
        } else {
            expense.id = expenses.length === 0 ? 1 : expenses[expenses.length - 1].id + 1;
            expenses.push(expense);
        }
        return expense;
    } catch (error) {
        console.error('Error while saving expense', error);
        throw error;
    }
}

export async function getLatestExpenses(count = 5) {
    try {
        // const response = axios.get(`${apiUrl}/transactions/latest?count=${count}`);
        await new Promise(resolve => setTimeout(resolve, 1000));
        return expenses.slice(-count);
    } catch (error) {
        console.error('Error while fetching latest expenses', error);
        throw error;
    }
}

function sumExpenses(expenses) {
    return expenses.reduce((total, expense) => total + expense.amount, 0);
}

export async function getSumOfMonthlyExpenses(month, year) {
    try {
        // const response = axios.get(`${apiUrl}/transactions/sum?month=${month}&year=${year}`);
        await new Promise(resolve => setTimeout(resolve, 1000));
        const monthlyExpenses = expenses.filter(
            expense =>
                new Date(expense.date).getMonth() === month && new Date(expense.date).getFullYear() === year
        );
        return sumExpenses(monthlyExpenses);
    } catch (error) {
        console.error('Error while fetching sum of expenses', error);
        throw error;
    }
}

export async function deleteExpense(id) {
    try {
        // const response = axios.delete(`${apiUrl}/transactions/${id}`);
        await new Promise(resolve => setTimeout(resolve, 1000));
        expenses.splice(expenses.findIndex(expense => expense.id === id), 1);
        return "Expense deleted successfully"
    } catch (error) {
        console.error('Error while deleting expense', error);
        throw error;
    }
}