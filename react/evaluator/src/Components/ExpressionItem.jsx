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
            <h3> expression = {expression.arithmeticExpression} </h3>
            <h3> result =  {expression.result} </h3>
            <h3> num of doubles = {expression.numOfDoubles} </h3>
            <Button onClick={() => setShow(true)}> change </Button>
            <Modal show={show} onHide={setShow}><ExpressionForm expression={expression} CreateOrUpdate={change} /> </Modal>
        </div>
    );
};
export default ExpressionItem;