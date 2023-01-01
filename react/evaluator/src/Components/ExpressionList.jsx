import React, {useState, useEffect} from 'react';
import ExpressionService from "../API/ExpressionService";
import Loader from "../UI/Loader/Loader";
import ExpressionForm from "./ExpressionForm";
import {Button, Modal} from "react-bootstrap";
import ExpressionItem from "./ExpressionItem";

const ExpressionList = () => {
    const [expressions, setExpressions] = useState([]);
    const [loading, setLoading] = useState(false);
    const [show, setShow] = useState(false);
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            fetchExpressions();
            setLoading(false);
        }, 1000);
    }, []);
    const add = (data) => {
        setExpressions([...expressions, data]);
        setShow(false);
    }

    async function fetchExpressions() {
        setExpressions(await ExpressionService.getAll());
    }

    return (
        <div>
            <div className="list">
                {loading ? (
                    <div style={{display: 'flex', justifyContent: 'center'}}>
                        <Loader/>
                    </div>
                ) : (
                    <div>
                        {expressions.length ? (
                            <div>
                                {expressions.map(expression =>
                                    <ExpressionItem expression={expression}/>
                                )}
                            </div>
                        ) : (
                            <h1> expressions not found! </h1>
                        )}
                    </div>
                )}
                <Button onClick={() => setShow(true)}> add </Button>
            </div>
            <Modal show={show} onHide={setShow}> <ExpressionForm CreateOrUpdate={add}/> </Modal>
        </div>

    );
};
export default ExpressionList;
