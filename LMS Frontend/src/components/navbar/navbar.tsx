import React, { useEffect, useState } from 'react';
import "./navbar.scss";
import { NavLink, useNavigate } from 'react-router-dom';
import Emoji from "../../assets/Images/Ellipse 1.svg"


const Navbar = () => {

  const navigate = useNavigate();
  const [dropdownOpen, setDropdownOpen] = useState(false);


  const navigateToBooks = () => {
    navigate('/books');
  };

  const navigateToUsers = () => {
    navigate('/users');
  };


  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

 

  return (
    <>
      <nav className="main-nav">
        <div className="logo">
          <h2> Codewalla Lab</h2>
        </div>

        <div className="menu-link">
          <ul>
                <li> 
                <a href='books' onClick={navigateToBooks} className="nav-link">Books</a>
                </li>

                <li> 
                <a href="users" onClick={navigateToUsers} className="nav-link">Users List</a>
                </li>
            </ul>
      
        </div>




        <div className="user-dropdown">
          <span className="user-name" onClick={toggleDropdown}>
            <div className='img'> <img src={Emoji}></img> </div>
            <div className='line-before-emoji'></div>
            Mrudula &#9662;
          </span>
          
        </div>
      </nav>
      <hr className="navbar-line" />

    </>
  );
};

export default Navbar;
export { };