import React, { Component } from 'react';
import UploadService from '../actions/csvReaderAction';

class CSVReader extends Component {

    state = {
        //Null as no file selected
        fileSelected: null,
        fileName: "Choose a CSV file",
        result: null,
        flag: false,
        headers: []
    };

    //Event called on selecting file
    onFileSelect = event => {

        this.setState({ fileName: event.target.files[0].name });
        //updating status of file selected
        this.setState({ fileSelected: event.target.files[0] });
    };

    //Event called on clicking upload button
    onFileUpload = event => {
        const formData = new FormData();
        // Update the formData object
        formData.append(
            "csvFile",
            this.state.fileSelected,
            this.state.fileName
        );

        UploadService.uploadCSV(formData)
            .then(res => {
                this.setState({ result: res.data, flag: true});
            });

    }

    render() {

        return (
            <div>
                <div className="csv-form">
                    <div className="input-group mb-3">
                        <div className="custom-file">
                            <input type="file" name="csvFile" className="custom-file-input" id="inputGroupFile02" onChange={this.onFileSelect} />
                            <label className="custom-file-label">{this.state.fileName}</label>
                        </div>
                        <div className="input-group-append">
                            <span className="input-group-text uploadBtn" onClick={this.onFileUpload} >Upload</span>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

}


export default CSVReader;
