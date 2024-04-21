import React, {useEffect, useState} from 'react'
import '../css/Transactions.css'
import {deleteTransaction, getTransactions} from '../services/TransactionService';
import {formatDateToIndian} from "../utils/dateFormatter";

export default function Expenses() {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(false);
    const [isUpdated, setIsUpdated] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        setLoading(true);
        setError(null);
        getTransactions()
            .then(r => setTransactions(r))
            .catch(e => setError(e))
            .finally(() => setLoading(false));
    }, [isUpdated]);

    const handleEdit = (id) => {

    };
    const handleDelete = (id) => {
        deleteTransaction(id)
            .then((r) => console.log(r))
            .catch(e => setError(e));
        setIsUpdated(!isUpdated);
    };

    return (
        <div className={'container table-responsive'}>
            {loading ?
                (<div className="spinner-grow text-success"><span className="visually-hidden">Loading...</span></div>) :
                <table className='table table-light table-striped table-bordered table-sm align-middle'>
                    <thead className={'table-dark'}>
                    <tr>
                        <th>Id</th>
                        <th>Date</th>
                        <th>Category</th>
                        <th>Amount</th>
                        <th>Details</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {transactions.map((transaction) => (
                        <tr key={transaction.id}>
                            <td>{transaction.id}</td>
                            <td>{formatDateToIndian(transaction.date)}</td>
                            <td>{transaction.category}</td>
                            <td>₹{transaction.amount}</td>
                            <td>{transaction.description}</td>
                            <td>
                                <button className='btn btn-primary me-2' onClick={() => handleEdit(transaction.id)}>
                                    Edit
                                </button>
                                <button className='btn btn-danger ms-2' onClick={() => handleDelete(transaction.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))
                    }
                    </tbody>
                </table>
            }
        </div>
    )
}