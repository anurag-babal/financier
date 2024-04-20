import {useEffect, useId, useState} from "react";
import {addTransaction} from "../services/TransactionService";
import {Transaction} from "../model/Transaction";
import {Alert} from "./Alert";
import {getCategories} from "../services/CategoryService";

function initTransaction() {
    // trans.date = new Date().toISOString().split('T')[0];
    return new Transaction();
}

function ExpenseForm({onClose, trans = initTransaction()}) {
    const [transaction, setTransaction] = useState(trans);
    const [loading, setLoading] = useState(false);
    const [data, setData] = useState(null);
    const [errors, setErrors] = useState({});
    const [categories, setCategories] = useState([]);
    const categoryDropDownId = useId();

    function handleChange(inputIdentifier, newValue) {
        setTransaction(prev => {
            return {
                ...prev,
                [inputIdentifier]: newValue
            };
        });
    }

    useEffect(() => {
        getCategories()
            .then(r => setCategories(r))
            .catch(e => setErrors(e));
    }, []);

    function validateForm() {
        const newErrors = {};
        if (!transaction.category) {
            newErrors.category = 'Category is required.';
        }
        if (transaction.amount <= 0) {
            newErrors.amount = 'Amount must be greater than 0.';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    }

    function handleSubmit(event) {
        event.preventDefault();
        if (validateForm()) {
            setLoading(true);
            setErrors({});

            addTransaction(transaction)
                .then(r => setData(r))
                .catch(e => setErrors(e))
                .finally(() => {
                    setLoading(false);
                    onClose();
                });
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'} htmlFor={categoryDropDownId}>Category:</label>
                <select id={categoryDropDownId}
                        className={'col'}
                        value={transaction.category}
                        onChange={(event) => handleChange('category', event.target.value)}
                >
                    {categories.map((category) => (
                        <option key={category.id} value={category.name}>
                            {category.name}
                        </option>
                    ))}
                </select>
                {errors.category && <Alert message={errors.category} type='danger'/>}
            </div>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'}>Amount:</label>
                <input
                    type='number'
                    className={'col'}
                    value={transaction.amount}
                    required={true}
                    onChange={(event) => handleChange('amount', event.target.value)}
                />
                {errors.amount && <Alert message={errors.amount} type='danger'/>}
            </div>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'}>Description:</label>
                <input
                    type='text'
                    className={'col'}
                    value={transaction.description}
                    onChange={(event) => handleChange('description', event.target.value)}
                />
            </div>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'}>Date:</label>
                <input
                    type='date'
                    className={'col'}
                    value={transaction.date}
                    required={true}
                    onChange={(event) => handleChange('date', event.target.value)}
                />
            </div>
            <button type={"submit"} className="btn btn-primary my-2" disabled={loading}>
                {
                    loading
                        ? (<span className="spinner-border spinner-border-sm"></span>)
                        : 'Add Expense'
                }
            </button>
            {data && <Alert message={data} type='success'/>}
        </form>
    );
}

export default ExpenseForm;