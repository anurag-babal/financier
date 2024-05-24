import React, {useContext, useEffect} from 'react';
import {AuthContext} from "../store/auth-context";

function Logout(props) {
    const {logout} = useContext(AuthContext);

    useEffect(() => {
        logout();
    }, []);

    return (
        <div></div>
    );
}

export default Logout;