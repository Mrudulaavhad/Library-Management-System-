import React from 'react';
import Navbar from './components/navbar/navbar';
import DataTable from './components/dataTable/BookTable';

import BookTable from './components/dataTable/BookTable';
import Error from './components/BookList/Error';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import UserTable from './components/dataTable/UserTable';
import CreateUser from './components/PopUp/CreateUser';








const App: React.FC = () => {
  return (
    <Router> 
    <div> 
     <Navbar />
      <Routes>
          <Route path="/" element={<DataTable />} />
          <Route path="/books" element={<BookTable />} />
          <Route path="/users" element={<UserTable />}/>
          <Route path= "/create-user" element={<CreateUser/>}/>
          <Route path="*" element={<Error />} />
        
        </Routes>
    </div>
    </Router>
      
  );
};



export default App;




