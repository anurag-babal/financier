import {createContext, useContext, useEffect, useState} from 'react';
import {Expense} from "../model/Expense";
import * as expenseService from "../services/ExpenseService";
import {AuthContext} from "./auth-context";

export const ExpenseContext = createContext({
    expense: {},
    expenses: [],
    dataChanged: false,
    setExpense: () => {},
    saveExpense: () => {},
    updateExpense: () => {},
    deleteExpense: () => {},
    getLatestExpenses: () => {},
    getSumOfMonthlyExpenses: () => {},
});

export default function ExpenseProvider({children}) {
    const {userId} = useContext(AuthContext);
    const [expense, setExpense] = useState(null);
    const [expenses, setExpenses] = useState([]);
    const [dataChanged, setDataChanged] = useState(false);

    useEffect(() => {
        expenseService.getExpensesByUserId(userId)
            .then(r => setExpenses(r))
            .catch(e => console.error(e));
        setExpense(new Expense());
    }, [dataChanged]);

    const getSumOfMonthlyExpensesHandler = async (userId, month, year) => {
        return expenseService.getSumOfMonthlyExpenses(userId, month, year)
            .then(r => r)
            .catch(e => console.error(e));
    }

    const getLatestExpensesHandler = async (userId, count) => {
        try {
            return await expenseService.getLatestExpenses(userId, count);
        } catch (error) {
            throw error.response.data;
        }
    }

    const handleChange = async (inputIdentifier, newValue) => {
        setExpense(prev => {
            return {
                ...prev,
                [inputIdentifier]: newValue
            };
        });
    }

    const saveExpenseHandler = async (userId) => {
        try {
            expense.userId = userId;
            await expenseService.saveExpense(expense);
            setDataChanged(!dataChanged);
            return "Expense saved successfully";
        } catch (error) {
            console.error('Error while saving expense', error);
            throw error;
        }
    }

    const setExpenseHandler = (id) => {
        expenseService.getExpense(id)
            .then(r => setExpense(r.data))
            .catch(e => console.error(e));
    }

    const deleteExpenseHandler = async (id) => {
        try {
            await expenseService.deleteExpense(id);
            setDataChanged(!dataChanged);
            return "Expense deleted successfully";
        } catch (error) {
            console.error('Error while deleting expense', error);
            throw error;
        }
    }

    return (
        <ExpenseContext.Provider value={{
            expense: expense,
            expenses: expenses,
            dataChanged: dataChanged,
            setExpense: setExpenseHandler,
            saveExpense: saveExpenseHandler,
            updateExpense: handleChange,
            deleteExpense: deleteExpenseHandler,
            getLatestExpenses: getLatestExpensesHandler,
            getSumOfMonthlyExpenses: getSumOfMonthlyExpensesHandler
        }}>
            {children}
        </ExpenseContext.Provider>
    );
}