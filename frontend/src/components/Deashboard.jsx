import {useContext, useEffect, useState} from "react";
import {ExpenseContext} from "../store/expense-context";
import {AuthContext} from "../store/auth-context";

const Dashboard = () => {
    const {getSumOfMonthlyExpenses, dataChanged} = useContext(ExpenseContext);
    const {userId} = useContext(AuthContext);

    const [sumOfMonthlyExpenses, setSumOfMonthlyExpenses] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const monthName = today.toLocaleDateString('default', {month: 'long'});

    useEffect(() => {
        setLoading(true);
        getSumOfMonthlyExpenses(userId, month + 1, year)
            .then(r => setSumOfMonthlyExpenses(r.data))
            .catch(e => setError(e))
            .finally(() =>
                setLoading(false)
            );
    }, [dataChanged]);

    return (
        <div className={'container my-2'}>
            {loading ?
                (
                    <div className="spinner-grow text-success">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                ) : (
                    <div className={'card shadow'}>
                        <div className={'card-header'}>
                            <h4 className={'card-title'}>{monthName + " " + year}</h4>
                        </div>
                        <div className={'card-body'}>
                            <div className={'card-body'}>
                                <h6 className={'card-text'}>Total Expenses</h6>
                                <h4 className={'card-text'}>₹{sumOfMonthlyExpenses}</h4>
                            </div>
                        </div>
                    </div>
                )
            }
        </div>
    );
}

export default Dashboard;