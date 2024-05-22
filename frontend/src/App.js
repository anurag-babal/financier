import './App.css';
import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from './pages/Login';
import Homepage from './pages/Homepage';
import ReportPage from './pages/reports';
import ExpenseForm from './components/ExpenseForm';
import PageNotFound from './components/PageNotFound';
import AuthProvider from "./store/auth-context";
import PrivateRoute from "./components/PrivateRoute";
import UserProvider from "./store/user-context";
import {registerCharts} from "./utils/register-charts";
import CategoryProvider from "./store/category-context";

registerCharts();

function App() {
    return (
        <div className="App">
            <AuthProvider>
                <UserProvider>
                    <CategoryProvider>
                        <BrowserRouter>
                            <Routes>
                                <Route path="login" element={<Login/>}/>
                                <Route element={<PrivateRoute/>}>
                                    <Route index element={<Homepage/>}/>
                                    <Route path="add-expense" element={<ExpenseForm/>}/>
                                    <Route path="reports" element={<ReportPage/>}/>
                                </Route>
                                <Route path="*" element={<PageNotFound/>}/>
                            </Routes>
                        </BrowserRouter>
                    </CategoryProvider>
                </UserProvider>
            </AuthProvider>
        </div>
    );
}

export default App;
