import styled from "styled-components";
import axios from "axios";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

const Wrapper = styled.div`
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
`

const Form = styled.form`
  display: flex;
  flex-direction: column;
  background-color: #7D8488;
  border-radius: 5px;
  padding: 20px;
`

const Input = styled.input`
  background-color: #3C3F41;
  margin: 10px 0 10px 0;
  border-radius: 5px;
  padding: 20px 10px;
  font-size: 18px;
  &::placeholder {
    color: #656B6E;
  }
`

const Button = styled.button`
  width: 100%;
  background-color: #3C3F41;
  margin: 10px 0 10px 0;
  border-radius: 5px;
  padding: 20px 10px;
  font-size: 18px;
  text-align: center;
  text-transform: uppercase;
  cursor: pointer;
  &:hover{
    background-color: #313335;
  }
`

const SignUp = () => {

    const navigate = useNavigate()
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [rePassword, setRePassword] = useState("")

    const onSubmit = (e) => {
        e.preventDefault()
        const body = {
            email,
            password,
            rePassword
        }

        const url = "http://localhost:8080/api/user/sign-up"
        const headers = new Headers();
        headers.append('Content-type', 'application/json');
        headers.append('Accept', 'application/json');
        headers.append('Access-Control-Allow-Origin', '*');

        fetch(url, {
            method: 'POST',
            mode: 'cors',
            body: JSON.stringify(body),
            headers
        }).then(async (response) => {
            if (response.ok) {
                navigate('/redirect')
            }
        }).catch((err) => {
            console.log(err);
        })
    }

    return (
        <Wrapper>
            <Form>
                <Input onChange={(e) => setEmail(e.target.value)} value={email} placeholder={'Email'}/>
                <Input onChange={(e) => setPassword(e.target.value)} value={password} placeholder={'Password'}/>
                <Input onChange={(e) => setRePassword(e.target.value)} value={rePassword} placeholder={'Repeat password'}/>
                <Button onClick={onSubmit}>Submit</Button>
            </Form>
        </Wrapper>
    )
};

export default SignUp