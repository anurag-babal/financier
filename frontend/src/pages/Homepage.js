import React, {useContext} from 'react'
import '../css/Homepage.css'
import Header from '../components/Header'
import Overview from '../components/Overview';
import Expenses from '../components/Expenses';
import ExpenseForm from "../components/ExpenseForm";
import Popup from "../components/Popup";
import ExpenseProvider from "../store/expense-context";
import {useNavigate} from "react-router-dom";

function Homepage() {
    const data = [
        {label: 'Jan', value: 9000},
        {label: 'Feb', value: 9500},
        {label: 'Mar', value: 13000},
        {label: 'Apr', value: 11000},
        {label: 'May', value: 7000},
        {label: 'Jun', value: 5000},
        {label: 'Jul', value: 4500},
        {label: 'Aug', value: 8000},
        {label: 'Sep', value: 9500},
        {label: 'Oct', value: 10000},
        {label: 'Nov', value: 12000},
        {label: 'Dec', value: 14000}
    ];
    const navigate = useNavigate();

    const [isPopupOpen, setIsPopupOpen] = React.useState(false);
    const handleOpenPopup = () => setIsPopupOpen(true);
    const handleClosePopup = () => setIsPopupOpen(false);
    const handleReport = () => navigate('/reports');

    return (
        <>
            <Header></Header>
            <ExpenseProvider>
                <div className={'container my-3'}>
                    <Overview data={data}></Overview>
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