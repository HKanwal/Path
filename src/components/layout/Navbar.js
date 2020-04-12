import React from 'react';
import PropTypes from 'prop-types';

import { Link } from 'react-router-dom';

const Navbar = ({ title, icon }) => {

  return (
    <div className='navbar bg-primary'>
      <h1>
        {title}<i className={icon} />
      </h1>
    </div>
  );
};

Navbar.propTypes = {
  title: PropTypes.string.isRequired,
  icon: PropTypes.string
};

Navbar.defaultProps = {
  title: 'PATH',
  icon: 'fas fa-car-alt'
};

export default Navbar;
