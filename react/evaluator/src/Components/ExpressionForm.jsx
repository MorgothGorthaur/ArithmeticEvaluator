import React, {useState} from 'react';
import {Button, Form} from 'react-bootstrap';
import Input from "../UI/Input/Input";
import ExpressionService from "../API/ExpressionService";

const ExpressionForm = ({expression, CreateOrUpdate}) => {
    const [arithmeticExpression, setArithmeticExpression] = useState('');
    const [result, setResult] = useState(0);
    const [numOfDoubles, setNumOfDoubles] = useState(0);
    const update = () => {
    };
    const add = (e) => {
        e.preventDefault();
        ExpressionService.addExpression(arithmeticExpression).then(data => {
            validation(data);
        })
    };

    const validation = (data) => {
        data.debugMessage ? alert(data.message) : CreateOrUpdate(data);
    }
    return (
        <div>
            <Form className="form" onSubmit={expression ? update : add}>
                <Input type="text" placeHolder="expression" value={arithmeticExpression}
                       onChange={e => setArithmeticExpression(e.target.value)}/>
                <Button type="submit"> {expression ? "update" : "create"} </Button>
            </Form>
            {expression ? (
                <>
                    <h1> result = {result} </h1>
                    <h1> numOfDoubles = {numOfDoubles}</h1>
                </>
            ) : (
                <>
                </>
            )}
        </div>
    );
};
export default ExpressionForm;