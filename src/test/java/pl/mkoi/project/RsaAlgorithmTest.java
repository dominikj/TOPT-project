package pl.mkoi.project;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RsaAlgorithmTest {
//  @Spy
//  private CryptoFacade cryptoUtils = new CryptoFacade();
//
//  @InjectMocks
//  private RsaCryptoService rsacryptoService = new RsaCryptoService(cryptoUtils);
//
//  private byte[] fileToTest;
//
//  @Test
//  public void testGenerateKeys() {
//    KeyPair keys = rsacryptoService.generateKeys(1024);
//    assertTrue(keys.getPrivateKey().toString().contains(";"));
//    assertTrue(keys.getPublicKey().toString().contains(";"));
//
//  }
//
//  @Test
//  public void testEncryptDecryptFromTable() throws ClassNotFoundException, IOException {
//    KeyPair keys = rsacryptoService.generateKeys(1024);
//    byte[] message = new byte[] {0, 34, 43, (byte) 250, (byte) 130};
//
//    byte[] encrypted = rsacryptoService.encrypt(message, (RsaKey) keys.getPublicKey());
//    byte[] decrypted = rsacryptoService.decrypt(encrypted, (RsaKey) keys.getPrivateKey());
//    assertTrue(Arrays.equals(Arrays.copyOfRange(message, 1, message.length), decrypted));
//  }
}
