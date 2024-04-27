import {createContext, useEffect, useState} from 'react';
import {Expense} from "../model/Expense";
import * as expenseService from "../services/ExpenseService";

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
    const [expense, setExpense] = useState(null);
    const [expenses, setExpenses] = useState([]);
    const [dataChanged, setDataChanged] = useState(false);

    useEffect(() => {
        expenseService.getExpenses()
            .then(r => setExpenses(r))
            .catch(e => console.error(e));
        setExpense(new Expense());
    }, [dataChanged]);

    const getSumOfMonthlyExpensesHandler = async (month, year) => {
        return expenseService.getSumOfMonthlyExpenses(month, year)
            .then(r => r)
            .catch(e => console.error(e));
    }

    const getLatestExpensesHandler = async (count) => {
        return expenseService.getLatestExpenses(count)
            .then(r => r)
            .catch(e => console.error(e));
    }

    const handleChange = async (inputIdentifier, newValue) => {
        setExpense(prev => {
            return {
                ...prev,
                [inputIdentifier]: newValue
            };
        });
    }

    const saveExpenseHandler = async () => {
        try {
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
            .then(r => setExpense(r))
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

    const ctxValue = {
        expense: expense,
        expenses: expenses,
        dataChanged: dataChanged,
        setExpense: setExpenseHandler,
        saveExpense: saveExpenseHandler,
        updateExpense: handleChange,
        deleteExpense: deleteExpenseHandler,
        getLatestExpenses: getLatestExpensesHandler,
        getSumOfMonthlyExpenses: getSumOfMonthlyExpensesHandler,
    }

    return (
        <ExpenseContext.Provider value={ctxValue}>
            {children}
        </ExpenseContext.Provider>
    );
}