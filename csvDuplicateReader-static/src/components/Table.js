import React from "react";

function Table(props) {

    return (
        <div className="results-table">
            <h3>{props.title}</h3>
            <table className="table table-bordered table-sm">
                <thead>
                <tr>
                    {props.headers.map(head => <th>{head}</th>) }
                </tr>
                </thead>
                <tbody>

                    {props.data.map(item => item.map(values => <tr>
                    <td>{values.id}</td>
                    <td>{values.first_name}</td>
                    <td>{values.last_name}</td>
                    <td>{values.company}</td>
                    <td>{values.email}</td>
                    <td>{values.address1}</td>
                    <td>{values.address2}</td>
                    <td>{values.zip}</td>
                    <td>{values.city}</td>
                    <td>{values.state_long}</td>
                    <td>{values.state}</td>
                    <td>{values.phone}</td>
                    </tr>))}

                </tbody>
            </table>
        </div>
    );
}

export default Table;
