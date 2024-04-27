import React from 'react';
import {formatDateToIndian} from "../utils/dateFormatter";

const ExpenseTable = ({ title, expenses, loading, error, isLoadingMore, onEdit, onDelete }) => {
    if (error) {
        return (
            <div className="container table-responsive">
                <div className="alert alert-danger" role="alert">
                    Error fetching expenses: {error.message}
                </div>
            </div>
        );
    }

    return (
        <div className="container">
            <h2>{title}</h2>
            {loading && (
                <div className="d-flex justify-content-center">
                    <div className="spinner-border text-primary" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            )}
            {isLoadingMore && (
                <div className="text-center mt-2">
                    <span className="spinner-border spinner-border-sm text-primary" role="status" aria-hidden="true"></span>
                    <span className="visually-hidden">Loading more...</span>
                </div>
            )}
            {!loading && !error && expenses.length > 0 && (
                <table className="table table-light table-striped table-bordered table-sm align-middle shadow">
                    <thead className="table-dark">
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
                    {expenses.map((expense) => (
                        <tr key={expense.id}>
                            <td>{expense.id}</td>
                            <td>{formatDateToIndian(expense.date)}</td>
                            <td>{expense.category}</td>
                            <td>₹{expense.amount.toFixed(2)}</td> {/* Format amount with 2 decimal places */}
                            <td>{expense.description}</td>
                            <td>
                                <button className="btn btn-primary me-2" onClick={() => onEdit(expense.id)}>
                                    Edit
                                </button>
                                <button className="btn btn-danger ms-2" onClick={() => onDelete(expense.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
            {!loading && !error && expenses.length === 0 && (
                <p className="text-muted text-center">No expenses found.</p>
            )}
        </div>
    );
};

export default ExpenseTable;