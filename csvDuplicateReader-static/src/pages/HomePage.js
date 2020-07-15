import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import Header from '../components/Header';
import CSVReader from '../components/CSVReader';

class HomePage extends Component {
  render() {
    return (
        <div className="home-page">
            <Header />
            <CSVReader />
        </div>
    );
  }
}

export default withRouter(HomePage);
