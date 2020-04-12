import React, { Fragment } from 'react';
import Main from './components/pages/Main';
import Auth from './components/auth/Auth';
import Navbar from './components/layout/Navbar';
import './App.css';

import AuthState from './context/auth/AuthState';
import MapState from './context/maps/MapState';

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

const App = () => {
  return (
    <div className="App">
      <AuthState>
        <MapState>
          <Router>
            <Fragment>
              <Navbar />
              <Switch>
                <Route exact path='/' component={Main} />
                <Route exact path='/auth' component={Auth}/>
              </Switch>
            </Fragment>
          </Router>
        </MapState>
      </AuthState>
    </div>
  );
}

export default App;
