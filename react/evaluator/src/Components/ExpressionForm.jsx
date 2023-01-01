import React, {useState, useEffect} from 'react';
import {Button, Form} from 'react-bootstrap';
import Input from "../UI/Input/Input";
import ExpressionService from "../API/ExpressionService";

const ExpressionForm = ({expression, CreateOrUpdate}) => {
    const [arithmeticExpression, setArithmeticExpression] = useState('');
    const update = (e) => {
        e.preventDefault();
        ExpressionService.changeExpression(expression.id, arithmeticExpression).then(data => {
            validation(data);
        })
    };
    const add = (e) => {
        e.preventDefault();
        ExpressionService.addExpression(arithmeticExpression).then(data => {
            validation(data);
        })
    };

    const validation = (data) => {
        data.debugMessage ? alert(data.debugMessage) : CreateOrUpdate(data);
    }

    useEffect(() => {
        if (expression) {
            setArithmeticExpression(expression.arithmeticExpression)
        }
    }, [expression])
    return (
        <div>
            <Form className="form" onSubmit={expression ? update : add}>
                <Input type="text" placeHolder="expression" value={arithmeticExpression}
                       onChange={e => setArithmeticExpression(e.target.value)}/>
                <Button type="submit"> {expression ? "update" : "create"} </Button>
            </Form>
            {expression ? (
                <>
                    <h1> result = {expression.result} </h1>
                    <h1> numOfDoubles = {expression.numOfDoubles}</h1>
                </>
            ) : (
                <>
                </>
            )}
        </div>
    );
};
export default ExpressionForm;