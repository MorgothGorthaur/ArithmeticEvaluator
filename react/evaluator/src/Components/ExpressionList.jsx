import React, {useState, useEffect} from 'react';
import ExpressionService from "../API/ExpressionService";
import Loader from "../UI/Loader/Loader";
import ExpressionForm from "./ExpressionForm";
import {Button, Modal} from "react-bootstrap";
import ExpressionItem from "./ExpressionItem";
import Input from "../UI/Input/Input";

const ExpressionList = () => {
    const [expressions, setExpressions] = useState([]);
    const [loading, setLoading] = useState(false);
    const [show, setShow] = useState(false);
    const [result, setResult] = useState('');
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            fetchExpressions();
        }, 1000);
    }, []);
    const add = (data) => {
        setExpressions([data, ...expressions]);
        setShow(false);
    }
    const update = (data) => {
        setExpressions([data, ...expressions.filter(ex => ex.id !== data.id)]);
        //customFind("all");
    }

    async function fetchExpressions() {
        setExpressions((await ExpressionService.getAll()).sort((a,b) => b.id - a.id));
        setLoading(false);
    }
    async function fetchExpressionsByResult() {
        setExpressions((await ExpressionService.getByResult(result)).sort((a,b) => b.id - a.id));
        setLoading(false);
    }
    async function fetchExpressionsWithLowerResults() {
        setExpressions((await ExpressionService.getWithLowerResults(result)).sort((a,b) => b.id - a.id));
        setLoading(false);
    }
    async function fetchExpressionsWithUpperResults() {
        setExpressions((await ExpressionService.getWithUpperResults(result)).sort((a,b) => b.id - a.id));
        setLoading(false);
    }
    const customFind = (value) => {
        setLoading(true);
        setTimeout(() => {
            if (value === "all") {
                fetchExpressions()
            } else if(result) {
                if (value === "by_result") fetchExpressionsByResult();
                if(value === "upper_then_result") fetchExpressionsWithUpperResults();
                if(value === "lower_then_result") fetchExpressionsWithLowerResults();
            } else {
                alert("result must be setted!")
                setExpressions([]);
                setLoading(false);
            }
        }, 1000);
    }
    return (
        <div>
            <div className="item">
                <Input type="number" placeHolder="required result" value={result}
                       onChange={e => setResult(e.target.value)}/>
                <select className="select" onClick={(e) => customFind(e.target.value)}>
                    <option value="all">find all</option>
                    <option value="by_result">find by result</option>
                    <option value="upper_then_result">find with higher results </option>
                    <option value="lower_then_result">find with lower results </option>
                </select>
            </div>
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
                                    <ExpressionItem expression={expression} update={update}/>
                                )}
                            </div>
                        ) : (
                            <h1> expressions not found! </h1>
                        )}
                    </div>
                )}
                <div style={{display: 'flex', justifyContent: 'center'}}>
                <Button onClick={() => setShow(true)}> add </Button>
                </div>
            </div>
            <Modal show={show} onHide={setShow}> <ExpressionForm CreateOrUpdate={add}/> </Modal>
        </div>

    );
};
export default ExpressionList;
