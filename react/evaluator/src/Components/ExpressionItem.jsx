import React, {useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import ExpressionForm from "./ExpressionForm";
const ExpressionItem = ({expression, update}) => {
    const [show, setShow] = useState(false);
    const change = (data) => {
        update(data);
        setShow(false);
    }
    return (
        <div className="item">
            <h1> expression = {expression.arithmeticExpression} </h1>
            <h2> result =  {expression.result} </h2>
            <h2> numOfDoubles = {expression.numOfDoubles} </h2>
            <Button onClick={() => setShow(true)}> change </Button>
            <Modal show={show} onHide={setShow}><ExpressionForm expression={expression} CreateOrUpdate={change} /> </Modal>
        </div>
    );
};
export default ExpressionItem;