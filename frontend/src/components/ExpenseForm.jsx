import {useContext, useId, useState} from "react";
import {Alert} from "./Alert";
import {ExpenseContext} from "../store/expense-context";
import {AuthContext} from "../store/auth-context";
import {CategoryContext} from "../store/category-context";

function ExpenseForm({onClose}) {
    const {userId} = useContext(AuthContext);
    const {categories} = useContext(CategoryContext);
    const {expense, saveExpense, updateExpense} = useContext(ExpenseContext);

    const [loading, setLoading] = useState(false);
    const [formErrors, setFormErrors] = useState({});
    const categoryDropDownId = useId();

    const handleChange = (inputIdentifier, newValue) => {
        updateExpense(inputIdentifier, newValue);
    }

    const validateForm = () => {
        const newErrors = {};
        if (!expense.category) {
            newErrors.category = 'Category is required.';
        }
        if (expense.amount <= 0) {
            newErrors.amount = 'Amount must be greater than 0.';
        }
        setFormErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if (validateForm()) {
            setLoading(true);
            setFormErrors({});
            saveExpense(userId)
                .then(r => setFormErrors({success: r}))
                .catch(e => setFormErrors({general: e.message}))
                .finally(() => {
                    setLoading(false)
                    onClose();
                });
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'} htmlFor={categoryDropDownId}>Category:</label>
                <select id={categoryDropDownId}
                        className={'form-control col'}
                        value={expense.category}
                        onChange={(event) => handleChange('category', event.target.value)}
                >
                    <option value={''}>--- Select Category ---</option>
                    {categories.map((category) => (
                        <option key={category.id} value={category.name}>
                            {category.name}
                        </option>
                    ))}
                </select>
                {formErrors.category && <Alert message={formErrors.category} type='danger'/>}
            </div>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'}>Amount:</label>
                <input
                    type='number'
                    className={'col'}
                    value={expense.amount}
                    required={true}
                    onChange={(event) => handleChange('amount', event.target.valueAsNumber)}
                />
                {formErrors.amount && <Alert message={formErrors.amount} type='danger'/>}
            </div>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'}>Description:</label>
                <input
                    type='text'
                    className={'col'}
                    value={expense.description}
                    onChange={(event) => handleChange('description', event.target.value)}
                />
            </div>
            <div className="form-group row align-items-center me-1 my-2">
                <label className={'col-4'}>Date:</label>
                <input
                    type='date'
                    className={'col'}
                    value={expense.date}
                    required={true}
                    onChange={(event) => handleChange('date', event.target.value)}
                />
            </div>
            <button type={"submit"} className="btn btn-primary my-2" disabled={loading}>
                {loading
                    ? (<span className="spinner-border spinner-border-sm"></span>)
                    : 'Add Expense'
                }
            </button>
        </form>
    );
}

export default ExpenseForm;