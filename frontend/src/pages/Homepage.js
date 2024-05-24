import {useContext, useEffect, useState} from 'react'
import '../css/Homepage.css'
import Header from '../components/Header'
import Overview from '../components/Overview';
import Expenses from '../components/Expenses';
import ExpenseForm from "../components/ExpenseForm";
import Popup from "../components/Popup";
import ExpenseProvider, {ExpenseContext} from "../store/expense-context";
import {useNavigate} from "react-router-dom";
import * as reportService from "../services/report-service";
import {AuthContext} from "../store/auth-context";
import {UserContext} from "../store/user-context";
import {months, prepareData} from "./reports";

function Homepage() {
    const navigate = useNavigate();
    const {getSumOfMonthlyExpenses} = useContext(ExpenseContext);
    const {userId} = useContext(AuthContext);
    const {user} = useContext(UserContext);

    const today = new Date();

    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [data, setData] = useState(null);

    const fetchReportData = async () => {
        reportService
            .getExpenseReport(
                userId,
                today.getFullYear().toString(),
                months[today.getMonth()+1],
                'All'
            )
            .then((r) => {
                setData(r.data);
                console.log('Report data:', r.data)
            })
            .catch((error) => {
                console.log('Error fetching report data:', error)
            });
    };

    useEffect(() => {
        fetchReportData(); // Fetch data on initial render and option changes
    }, []);

    const handleOpenPopup = () => setIsPopupOpen(true);
    const handleClosePopup = () => setIsPopupOpen(false);
    const handleReport = () => navigate('/reports');

    return (
        <>
            <Header></Header>
            <ExpenseProvider>
                <div className={'container my-3'}>
                    <Overview data={prepareData(data)}></Overview>
                    <div className={'my-3'}>
                        <button className='btn btn-primary btn-lg me-2' onClick={handleOpenPopup}>Add Expense</button>
                        <button className='btn btn-primary btn-lg ms-2' onClick={handleReport}>Expense Report</button>
                    </div>
                    <div className={'my-2'}>
                        <Expenses openForm={handleOpenPopup}/>
                    </div>
                    {isPopupOpen &&
                        <Popup title="Add Expense" onClose={handleClosePopup}>
                            {<ExpenseForm onClose={handleClosePopup}/>}
                        </Popup>
                    }
                </div>
            </ExpenseProvider>
        </>
    )
}

export default Homepage