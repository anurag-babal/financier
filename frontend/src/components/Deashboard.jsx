import {useContext, useEffect, useState} from "react";
import {ExpenseContext} from "../store/expense-context";
import {AuthContext} from "../store/auth-context";
import {UserContext} from "../store/user-context";
import {Card, Col, Row, Spinner} from "react-bootstrap";

const Dashboard = () => {
    const {getSumOfMonthlyExpenses, dataChanged} = useContext(ExpenseContext);
    const {userId} = useContext(AuthContext);
    const {user} = useContext(UserContext);

    const [sumOfMonthlyExpenses, setSumOfMonthlyExpenses] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const monthName = today.toLocaleDateString('default', {month: 'long'});
    const textColor = user ? sumOfMonthlyExpenses > user.budget ? 'text-danger' : 'text-success' : 'text-danger';
    const budget = user ? user.budget : 0;

    useEffect(() => {
        setLoading(true);
        getSumOfMonthlyExpenses(userId, month + 1, year)
            .then(r => setSumOfMonthlyExpenses(r.data))
            .catch(e => setError(e))
            .finally(() =>
                setLoading(false)
            );
    }, [dataChanged]);

    if (loading) {
        return (
            <div className="container my-2">
                <Spinner animation="grow" variant="success" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
            </div>
        );
    }

    return (
        <div className="container my-2">
            <Card border="light" className="shadow">
                <Card.Header>
                    <Card.Title>{monthName + " " + year}</Card.Title>
                </Card.Header>
                <Card.Body>
                    <Row className="justify-content-md-center">
                        <Col md="auto">
                            <h6 className="card-text">Total Expenses</h6>
                        </Col>
                        <Col md="auto">
                            <h6 className="card-text">Budget</h6>
                        </Col>
                    </Row>
                    <Row className="justify-content-md-center">
                        <Col md="auto">
                            <h4 className={`card-text ${textColor}`}>₹{sumOfMonthlyExpenses}</h4>
                        </Col>
                        <Col md="auto">
                            <h4 className="card-text">/</h4>
                        </Col>
                        <Col md="auto">
                            <h4 className={`card-text ms-auto ${textColor}`}>₹{budget}</h4>
                        </Col>
                    </Row>
                </Card.Body>
            </Card>
        </div>
    );
}

export default Dashboard;