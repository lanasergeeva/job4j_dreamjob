import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

<<<<<<< HEAD
public class AppTest  {
=======
public class AppTest {
>>>>>>> origin/master
    @Test
    public void whenSum() {
        App ap = new App();
        assertThat(ap.sum(1, 3), is(4));
    }
}