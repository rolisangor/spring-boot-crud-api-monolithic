import {useEffect} from "react";

const Crud = () => {

    const token = sessionStorage.getItem("access_token")

    useEffect(() => {
        fetch("http://localhost:8080/api/user?page=0&size=10", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(async (response) => {
                const data = await response.json();
                console.log(data)
            }).catch((err) => {
            console.log(err);
        })
    }, [])

    return (
        <div>
            <h1>Crud operation</h1>
        </div>
    )
};

export default Crud