import {useEffect, useState} from "react";

export function Alert(props) {
    const [isVisible, setIsVisible] = useState(true);
    const {message, type} = props;

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsVisible(false);
        }, 3000);
        return () => clearTimeout(timer);
    }, []);

    return (
        <>
            {isVisible && <div className={`alert alert-${type}`}>{message}</div>}
        </>
    );
}