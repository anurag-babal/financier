import {createContext, useState} from "react";
import axios from "axios";
import * as reportService from "../services/report-service";

export const ReportContext = createContext({
    reports: [],
    dataChanged: () => {},
    getMonthExpenses: () => {},
    getYearExpenses: () => {},
    getExpenseSummary: () => {},
    getExpenseReport: () => {},
});

export default function ReportProvider({children}) {
    const [dataChanged, setDataChanged] = useState(false);

    const getExpenseReportHandler = async (userId, year, month, category) => {
        const response = reportService.getExpenseReport(userId, year, month, category);
        return response.data;
    }

    const dataChangedHandler = () => {
        setDataChanged(!dataChanged);
    }

    return (
        <ReportContext.Provider value={{
            reports: [],
            dataChanged: dataChangedHandler,
            getExpenseReport: getExpenseReportHandler
        }}>
            {children}
        </ReportContext.Provider>
    );
}