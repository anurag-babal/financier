import './App.css';
import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from './pages/Login';
import Homepage from './pages/Homepage';
import ExpenseForm from './components/ExpenseForm';
import PageNotFound from './components/PageNotFound';
import AuthProvider from "./store/auth-context";
import PrivateRoute from "./components/PrivateRoute";
import UserProvider from "./store/user-context";

function App() {
    return (
        <div className="App">
            <AuthProvider>
                <UserProvider>
                    <BrowserRouter>
                        <Routes>
                            <Route path="login" element={<Login/>}/>
                            <Route element={<PrivateRoute/>}>
                                <Route index element={<Homepage/>}/>
                                <Route path="add-expense" element={<ExpenseForm/>}/>
                            </Route>
                            <Route path="*" element={<PageNotFound/>}/>
                        </Routes>
                    </BrowserRouter>
                </UserProvider>
            </AuthProvider>
        </div>
    );
}

export default App;
