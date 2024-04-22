import React, {useContext} from 'react'
import '../css/Homepage.css'
import Header from '../components/Header'
import Overview from '../components/Overview';
import Expenses from '../components/Expenses';
import ExpenseForm from "../components/ExpenseForm";
import Popup from "../components/Popup";
import {deleteTransaction} from "../services/TransactionService";
import TransactionContextProvider, {TransactionContext} from "../store/transaction-context";

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

    const { initTransaction } = useContext(TransactionContext);

    const [isPopupOpen, setIsPopupOpen] = React.useState(false);
    const handleOpenPopup = () => setIsPopupOpen(true);
    const handleClosePopup = () => {
        setIsPopupOpen(false);
        initTransaction();
    }

    return (
        <>
            <Header></Header>
            <TransactionContextProvider>
                <div className='home'>
                    <div className='overview'>
                        <Overview data={data}></Overview>
                    </div>
                    <div className='add-btn'>
                        <button className='add-exp-btn' onClick={handleOpenPopup}>+ Add Expense</button>
                    </div>
                    {
                        isPopupOpen &&
                        <Popup
                            title="Add Expense"
                            onClose={handleClosePopup}
                            component={<ExpenseForm onClose={handleClosePopup}/>}
                        />
                    }
                    <div className='recent-transactions'>
                        <Expenses openForm={handleOpenPopup}/>
                    </div>
                </div>
            </TransactionContextProvider>
        </>
    )
}

export default Homepage