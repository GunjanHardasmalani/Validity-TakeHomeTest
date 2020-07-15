import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';

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
