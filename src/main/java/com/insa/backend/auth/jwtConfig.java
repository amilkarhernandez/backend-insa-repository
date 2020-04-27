package com.insa.backend.auth;

public class jwtConfig {
public static final String LLAVE_SECRETA= "nucleos.lab.corasystem.colegios.0704";
	
	public static final String RSA_PRIVADA ="-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEA08vZ800N/X/hC8QCg5L795jKs5pTc8qV+0Q4WWy9gfuwnWlY\r\n" + 
			"0mqEfDIf9ksbqRtGPdW6IoVpzWL3dqiRa+/S+xKuswL1d2+N81DMEvbJdWtWlmOs\r\n" + 
			"SsILzzZCtbZ9LTr+D76O1LwmbcRpA73VDrxrbCipvTkxfGfjTQV64sbJPJ88h1jX\r\n" + 
			"0+D/QvSEMszGPIlG480rMGFOfdq7CcN5WcqTgCthO+JwYwrb+xYeQcujixr3A8mF\r\n" + 
			"licfO8o5YgzmJRWmJGA45EBnNVXEGkYuNdzkp33VESixy8bkEAQGJ+bq7XUiezsc\r\n" + 
			"39oEHp7X7Fkh/IRcLnKEKm2kaQD354YCw9dNIwIDAQABAoIBAHn8uLzkzcELjaBM\r\n" + 
			"NjJ4Tz4Nt/p4UcaokXgWk+oVspUSVWhky+7twP8Tk8s0+WqONgfZeQr3ErhkXynt\r\n" + 
			"70xe5oMMAKlEdANlsjnJeLEXiXNi7o1SPWMVnt2rYALvZ+vMRZbl57hp4ixo6x9n\r\n" + 
			"kDabmbfDN/iv32vLHmU3Wc8SNcf+1tRqQBrmCayr2vjNjJ6XqffPzlf69UdNU788\r\n" + 
			"dlSgfRZy0TjkvpRY0z9bXYmWHSQsOK83/By6/cEtZ2iqpuAUWmPvYV26fvtWF9dx\r\n" + 
			"Qcc5NidAm2y9PRbm++TiDsign3MWY0s+2N0cJAjlVBuROa7Tn4VTN/Bb20wB7wg4\r\n" + 
			"wcIuzjkCgYEA8rbVLPqGjgCWtUYiitOLNb1JPkJTJPbDAfghySXUQDfbuNvjRwm+\r\n" + 
			"O3ZHeiPMMjl7UV9m50fp1dSfhZMMqm4QprNdKlcQaZgVk3UDsGMzGTPWEQC5aesy\r\n" + 
			"Bw4FDiBcMR4dS7SYyz3lvdCNWTS7oT4UtLNKeTiBfVNTXM4jCJbipc8CgYEA32PD\r\n" + 
			"tMwWNHJye3Q4T+CSGARzbj5hFXwyWUT00XKdkj83lzR2nA8TDPYvN6jqcotga2G9\r\n" + 
			"pOLWyYHI+gPtXLDYquGuy5eVz41VOEPO/hueBMUix6gDu6iWvklIJ9g8ForI94Pd\r\n" + 
			"RiaoULcO1h/TPCaCtZMNGJmb1Dya8QhkcvafDG0CgYEAhVMphquXfimUn78G+nt5\r\n" + 
			"3Os9cJW+Vm+2bz5+UKAXF6+XYtRm5H7VoJQ4bz96y5wHwYtGW4WFTZCekCFWs8gC\r\n" + 
			"HZApfTiWK2r/byC4Bgx35UA0NuLO9v/bW0S4QipEejawoQVzaTF3Ie2Nt5Lf/pK7\r\n" + 
			"jQdvwWbouo8rG6+gTveTSMECgYAIQXGcHua7LXeZpGPoX85GYPO7IUcJOjvKHMhH\r\n" + 
			"bUrxerCdP+0aO1xi4D5CelWfwhLgJQ6TJ2b0r30z8C03fbmkJw8Eqyd4MiryF19w\r\n" + 
			"+KhQWNC+PDFXIBXiFmc6Qb2Gv75Jl2/4Bbm2se00cJyn3mx6NWMIg7TElTDMs5iy\r\n" + 
			"vvaFyQKBgQDODUk9o242Op2LE47EKgCN6r8DLr+82o3cvRtBIDvKS1USQ64npObe\r\n" + 
			"T+Ofmyo0VuGTi6wDmptobQB7PVjALqbLySvU6Iiy3NTdE3cCrvkE2Nt7uKrdcMeU\r\n" + 
			"G80t9aNrul/quL07QYhG6/yHz5pxkW2H59hHI0hBkPZq4IUEKTr8mg==\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA08vZ800N/X/hC8QCg5L7\r\n" + 
			"95jKs5pTc8qV+0Q4WWy9gfuwnWlY0mqEfDIf9ksbqRtGPdW6IoVpzWL3dqiRa+/S\r\n" + 
			"+xKuswL1d2+N81DMEvbJdWtWlmOsSsILzzZCtbZ9LTr+D76O1LwmbcRpA73VDrxr\r\n" + 
			"bCipvTkxfGfjTQV64sbJPJ88h1jX0+D/QvSEMszGPIlG480rMGFOfdq7CcN5WcqT\r\n" + 
			"gCthO+JwYwrb+xYeQcujixr3A8mFlicfO8o5YgzmJRWmJGA45EBnNVXEGkYuNdzk\r\n" + 
			"p33VESixy8bkEAQGJ+bq7XUiezsc39oEHp7X7Fkh/IRcLnKEKm2kaQD354YCw9dN\r\n" + 
			"IwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
	
	

}

