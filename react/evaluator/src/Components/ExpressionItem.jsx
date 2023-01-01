const ExpressionItem = ({expression}) => {
    return (
        <div className="item">
            <h1> expression = {expression.arithmeticExpression} </h1>
            <h2> result =  {expression.result} </h2>
            <h2> numOfDoubles = {expression.numOfDoubles} </h2>
        </div>
    );
};
export default ExpressionItem;