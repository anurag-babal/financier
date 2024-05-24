import React, {useContext, useState} from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {AuthContext} from "../store/auth-context";

function Signup() {
    const { register } = useContext(AuthContext);

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [budget, setBudget] = useState('');
    const [dob, setDob] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        register({username, password, firstName, lastName, email, phoneNumber, dateOfBirth: dob, budget})
        setFirstName('');
        setLastName('');
        setPhoneNumber('');
        setEmail('');
        setPassword('');
        setDob('');
        setBudget('');
    };

    return (
        <div className="container mt-5">
            <h1 className="mb-4">Sign Up</h1>
            <Form onSubmit={handleSubmit}>
                <Row className="my-3">
                    <Col xs={3}>
                        <Form.Label>Username</Form.Label>
                    </Col>
                    <Col xs={9}>
                        <Form.Control
                            type="text"
                            placeholder="Enter username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                        <Form.Label>Password</Form.Label>
                    </Col>
                    <Col xs={9}>
                        <Form.Control
                            type="password"
                            placeholder="Enter Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                            <Form.Label>First Name</Form.Label>
                        </Col>
                    <Col xs={9}>
                            <Form.Control
                                type="text"
                                placeholder="Enter First Name"
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                required
                            />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                            <Form.Label>Last Name</Form.Label>
                        </Col>
                    <Col xs={9}>
                            <Form.Control
                                type="text"
                                placeholder="Enter Last Name"
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                required
                            />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                        <Form.Label>Email address</Form.Label>
                    </Col>
                    <Col xs={9}>
                        <Form.Control
                            type="email"
                            placeholder="Enter email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                            <Form.Label>Mobile Number</Form.Label>
                    </Col>
                    <Col xs={9}>
                            <Form.Control
                                type="tel"
                                placeholder="Enter Mobile Number"
                                value={phoneNumber}
                                onChange={(e) => setPhoneNumber(e.target.value)}
                                required
                            />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                        <Form.Label>Date of Birth</Form.Label>
                    </Col>
                    <Col xs={9}>
                        <Form.Control
                            type="date"
                            value={dob}
                            onChange={(e) => setDob(e.target.value)}
                            required
                        />
                    </Col>
                </Row>
                <Row className="my-3">
                    <Col xs={3}>
                            <Form.Label>Budget</Form.Label>
                        </Col>
                    <Col xs={9}>
                            <Form.Control
                                type="number"
                                placeholder="Enter Budget"
                                value={budget}
                                onChange={(e) => setBudget(e.target.value)}
                                required
                            />
                    </Col>
                </Row>

                <Button className="my-3" variant="primary" type="submit">
                    Sign Up
                </Button>
            </Form>
        </div>
    );
}

export default Signup;