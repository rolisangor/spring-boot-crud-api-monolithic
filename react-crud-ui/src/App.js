import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Redirect from './components/Redirect';
import Home from './components/Home';
import Crud from "./components/Crud";

function App() {

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/redirect" element={<Redirect />} />
            <Route path="/crud" element={<Crud />} />
            <Route path="/authorized" element={<Redirect link='/crud' />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
