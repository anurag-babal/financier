import {createContext, useState} from 'react';
import {Transaction} from "../model/Transaction";
import * as transactionService from "../services/TransactionService";

export const TransactionContext = createContext({
    transaction: {},
    saveTransaction: (_) => {},
    updateTransaction: (_, __) => {},
    initTransaction: () => {}
});

export default function TransactionContextProvider({children}) {
    const [transaction, setTransaction] = useState(new Transaction());

    function handleChange(inputIdentifier, newValue) {
        setTransaction(prev => {
            return {
                ...prev,
                [inputIdentifier]: newValue
            };
        });
    }

    function saveTransaction() {
        transactionService.addTransaction(transaction)
            .then(r => console.log(r))
            .catch(e => console.error(e))
            .finally(() => {
                initTransaction();
            })
    }

    function initTransaction() {
        setTransaction(new Transaction());
    }

    const ctxValue = {
        transaction: transaction,
        saveTransaction: saveTransaction,
        updateTransaction: handleChange,
        setTransaction: setTransaction,
        initTransaction: initTransaction
    }

    return (
        <TransactionContext.Provider value={ctxValue}>
            {children}
        </TransactionContext.Provider>
    );
}