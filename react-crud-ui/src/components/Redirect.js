import { useEffect } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { Buffer } from "buffer";

const Redirect = () => {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    useEffect(() => {
        if(searchParams?.get('code')){
            const code = searchParams?.get('code');
            const client = 'client';
            const secret = 'secret';
            const headers = new Headers();
            headers.append('Content-type', 'application/json');
            headers.append('Authorization', `Basic ${Buffer.from(`${client}:${secret}`).toString('base64')}`);

            const verifier = localStorage.getItem('codeVerifier');
            console.log("VERIFIER: " + verifier)
            
            const initialUrl = 'http://localhost:8080/oauth2/token?client_id=client&redirect_uri=http://127.0.0.1:3000/authorized&grant_type=authorization_code';
            const url = `${initialUrl}&code=${code}&code_verifier=${verifier}`;

            fetch(url, {
                method: 'POST',
                mode: 'cors',
                headers
            }).then(async (response) => {
                const token = await response.json();
                if(token?.access_token && token?.refresh_token) {
                    sessionStorage.setItem('access_token', token.access_token);
                    sessionStorage.setItem('refresh_token', token.refresh_token);
                    navigate('/crud');
                }
            }).catch((err) => {
                console.log(err);
            })
        }
    }, []);
    useEffect(() => {
        if(!searchParams?.get('code')){
            const codeChallenge = localStorage.getItem('codeChallenge');
            console.log("CODE_CHALLENGE: " + codeChallenge)
            const link = `http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=${codeChallenge}&code_challenge_method=S256`;

            window.location.href = link;
        }
    }, []);
    return <p>Redirecting ...</p>
}

export default Redirect;