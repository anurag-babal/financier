import {useContext} from "react";
import {AuthContext} from "../store/auth-context";
import {Navigate, Outlet} from "react-router-dom";

const PrivateRoute = ({ redirectPath = '/login', children }) => {
    const { isAuthenticated} = useContext(AuthContext);

    if (!isAuthenticated) {
        return <Navigate to={redirectPath} replace />;
    }

    return children ? children : <Outlet />;
}

export default PrivateRoute;