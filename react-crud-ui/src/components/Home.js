import {generateCodeChallenge, generateCodeVerifier} from "../pkce/pkce";
import styled from 'styled-components';
import {useNavigate} from "react-router-dom";

const Wrapper = styled.div`
  background-color: #3C3F41;
  height: 100vh;
  color: aliceblue;
  display: flex;
  flex-direction: column;
  align-items: center;
`

const H1 = styled.h1`
  font-size: 100px;
`

const ButtonWrapper = styled.div`
  display: flex;
  margin-top: 50px;
`

const Button = styled.button`
  border-radius: 5px;
  background-color: #7D8488;
  padding: 20px 50px;
  cursor: pointer;
  font-size: 30px;
  margin: 10px;
  &:hover {
    background-color: #656B6E;
  }
`

const Home = () => {
    const verifier = generateCodeVerifier();
    localStorage.setItem('codeVerifier', verifier);
    const codeChallenge = generateCodeChallenge();
    localStorage.setItem('codeChallenge', codeChallenge);

    const navigate = useNavigate()

    return <>
        <Wrapper>
            <H1>Spring boot crud api</H1>
            <ButtonWrapper>
                <Button onClick={() => navigate('/redirect')}>Sign-in</Button>
                <Button onClick={() => navigate('/sign-up')}>Sign-up</Button>
            </ButtonWrapper>

        </Wrapper>
    </>;
}

export default Home;