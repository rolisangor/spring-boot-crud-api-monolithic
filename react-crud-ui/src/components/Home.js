import {generateCodeChallenge, generateCodeVerifier} from "../pkce/pkce";
import {Link} from "react-router-dom";

const Home = () => {
    const verifier = generateCodeVerifier();
    localStorage.setItem('codeVerifier', verifier);
    const codeChallenge = generateCodeChallenge();
    localStorage.setItem('codeChallenge', codeChallenge);

    return <>
        <div className="header">
            <h1>Home</h1>
            <Link to={'/redirect'}>Login</Link>
        </div>
    </>;
}

export default Home;