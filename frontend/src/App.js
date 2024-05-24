import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from './pages/Login';
import Homepage from './pages/Homepage';
import ReportPage from './pages/reports';
import PageNotFound from './components/PageNotFound';
import AuthProvider from "./store/auth-context";
import PrivateRoute from "./components/PrivateRoute";
import UserProvider from "./store/user-context";
import {registerCharts} from "./utils/register-charts";
import CategoryProvider from "./store/category-context";
import Signup from "./pages/signup";
import Logout from "./pages/logout";

registerCharts();

function App() {
    return (
        <div className="App">
            <AuthProvider>
                <UserProvider>
                    <CategoryProvider>
                        <BrowserRouter>
                            <Routes>
                                <Route path="/login" element={<Login/>}/>
                                <Route path="/register" element={<Signup/>}/>
                                <Route element={<PrivateRoute/>}>
                                    <Route index element={<Homepage/>}/>
                                    <Route path="/reports" element={<ReportPage/>}/>
                                </Route>
                                <Route path="/logout" element={<Logout/>}/>
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
