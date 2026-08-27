import { useRef, useState, useEffect } from "react";
import { faCheck, faTimes, faInfoCircle} from "@fortawesome/free-solid-svg-icons"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome"
import axios from './api/axios';

const USER_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,24}$/;

const Register = () => {

    const userRef = useRef<HTMLInputElement>(null);
    const errRef = useRef<HTMLParagraphElement>(null);

    const [user, setUser] = useState("");
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [pwd, setPwd] = useState("");
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchPwdFocus, setMatchPwdFocus] = useState(false);

    const [err, setErr] = useState("");
    const [success, setSuccess] = useState(false);

    //Focus on username initially
    useEffect(() => {
        userRef.current.focus();
    }, [])

    //Test username
    useEffect(() => {
        const result = USER_REGEX.test(user);
        console.log(result);
        console.log(user);
        setValidName(result);
    }, [user])

    useEffect(() => {
        const result: boolean = PWD_REGEX.test(pwd);
        console.log(result);
        console.log(pwd);
        setValidPwd(result);
        const match: boolean = pwd === matchPwd;
        setValidMatch(match);
    }, [pwd, matchPwd]);

    useEffect(() => {
        setErr('');
    }, [user, pwd, matchPwd]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        //Checks that data is entered if submit is unlawfully activated
        const v1: boolean = USER_REGEX.test(user);
        const v2: boolean = PWD_REGEX.test(pwd);
        if(!v1 || !v2) {
            setErr("Invalid username or password");
            return;
        }
        console.log("Submitting");
        setSuccess(true);
    }

    return (
        <>
            {success ? (
                <section>
                    <h1>Success!</h1>
                    <p>
                        <a href={"/login"}>Sign in</a>.
                    </p>
                </section>) : (
        <section>
            <p ref={errRef} className={err ? "errmsg" : "offscreen"} aria-live="assertive">
                {err}
            </p>
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor={"username"}>
                    Username:
                        <FontAwesomeIcon icon={faCheck} className={validName ? "valid" : "hide"}/>
                        <FontAwesomeIcon icon={faTimes}  className={validName || !user ? "hide" : "invalid"}/>
                </label>
                <input type="text"
                       id="username"
                       ref={userRef}
                       autoComplete="off"
                       onChange={(e) => setUser(e.target.value)}
                       required
                       aria-invalid={validName ? "false" : "true"}
                       aria-describedby="uidnote"
                       onFocus={() => setUserFocus(true)}
                       onBlur={() => setUserFocus(false)}
                />
                <p id={"uidnote"} className={userFocus && user && !validName ? "instructions" : "offscreen"}>
                    <FontAwesomeIcon icon={faInfoCircle} />
                    4 to 24 characters.<br />
                    Must begin with a letter.<br />
                    Letters, numbers, underscores, hyphens allowed.
                </p>
                <label htmlFor={"password"}>
                    Password:
                        <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"}/>
                        <FontAwesomeIcon icon={faTimes} className={validPwd || !pwd ? "hide" : "invalid"}/>
                </label>
                <input type="password"
                       id="password"
                       onChange={(e) => setPwd(e.target.value)}
                       value={pwd}
                       aria-invalid={validPwd ? "false" : "true"}
                       aria-describedby="pwdnote"
                       onFocus={() => setPwdFocus(true)}
                       onBlur={() => setPwdFocus(false)}
                />
                <p id={"pwdnote"} className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                    <FontAwesomeIcon icon={faInfoCircle} />
                    8 to 24 characters.<br />
                    Must contain at least one uppercase letter, one lowercase letter, one number and one special character.<br/>
                <span aria-label={"exclamation mark"}>!</span>
                    <span aria-label={"at symbol"}>@</span>
                    <span aria-label={"dollar sign"}>$</span>
                    <span aria-label={"percent sign"}>%</span>
                    <span aria-label={"hashtag"}>#</span>
                </p>
                <label htmlFor={"confirm_password"}>
                    Match Password:
                    <FontAwesomeIcon icon={faCheck} className={validMatch && matchPwd ? "valid" : "hide"}/>
                    <FontAwesomeIcon icon={faTimes} className={validMatch || !matchPwd ? "hide" : "invalid"}/>
                </label>
                <input type="password"
                       id="confirm_pwd"
                       onChange={(e) => setMatchPwd(e.target.value)}
                       required
                       value={matchPwd}
                       aria-invalid={validMatch ? "false" : "true"}
                       aria-describedby="confirmnote"
                       onFocus={() => setMatchPwdFocus(true)}
                       onBlur={() => setMatchPwdFocus(false)}
                />
                <p id={"confirmnote"} className={matchPwdFocus && !validMatch ? "instructions" : "offscreen"}>
                    <FontAwesomeIcon icon={faInfoCircle} />
                    Must match previous password.
                </p>
                <button type="submit"
                    disabled={!validName || !validPwd || !validMatch ? true : false}>
                    Register account
                </button>
            </form>
            <p>Already registered?<br/>
            <span className={"line"}>
                {/*Put a link to the login page here*/}
                <a href={"/login"}>Login</a>
            </span>
            </p>
        </section>
            )}
        </>
    )
}

export default Register;