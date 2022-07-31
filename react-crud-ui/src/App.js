import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Redirect from './components/Redirect';
import Home from './components/Home';
import Crud from "./components/Crud";
import SignUp from "./components/SignUp";
import styled from "styled-components";

const AppWrapper = styled.div`
  background-color: #3C3F41;
  min-height: 100vh;
  color: aliceblue;
`

const App = () => {
  return (
    <AppWrapper>
      <BrowserRouter>
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/redirect" element={<Redirect />} />
            <Route path="/sign-up" element={<SignUp />} />
            <Route path="/crud" element={<Crud />} />
            <Route path="/authorized" element={<Redirect link='/crud' />} />
        </Routes>
      </BrowserRouter>
    </AppWrapper>
  );
}

export default App;
