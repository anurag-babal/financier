import React, {useContext, useEffect, useState} from 'react'
import '../css/Transactions.css'
import {ExpenseContext} from "../store/expense-context";
import ExpenseTable from "./ExpenseTable";
import {AuthContext} from "../store/auth-context";

export default function Expenses({openForm}) {
    const {dataChanged, getLatestExpenses, setExpense, deleteExpense} = useContext(ExpenseContext);
    const {userId} = useContext(AuthContext);

    const [expenses, setExpenses] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isLoadingMore, setIsLoadingMore] = useState(false);

    const title = 'Recent Expenses';

    useEffect(() => {
        setLoading(true);
        getLatestExpenses(userId, 20)
            .then(r => {
                console.log(r)
                setExpenses(r.data)
            })
            .catch(e => {
                console.error(e)
                setError(e.message)
            })
            .finally(() => setLoading(false));
    }, [dataChanged]);

    const handleEdit = (id) => {
        setExpense(id);
        openForm();
    };

    const handleDelete = (id) => {
        setLoading(true);
        deleteExpense(id)
            .then((r) => console.log(r))
            .catch(e => setError(e))
            .finally(() => setLoading(false));
    };

    return (
        <ExpenseTable title={title} expenses={expenses} loading={loading} error={error} isLoadingMore={isLoadingMore}
                      onEdit={handleEdit} onDelete={handleDelete}
        />
    )
}