package de.java.domain;

import static de.java.domain.OrderState.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

public class OrderStateTest {

  @Test public void
  openIsPreceededByPosting() {
    assertThat(OPEN, is(succeededBy(POSTING)));
  }

  @Test public void
  postingIsPreceededByOrdered() {
    assertThat(POSTING, is(succeededBy(ORDERED)));
  }
 
  @Test public void
  orderedIsPreceedByFinished() {
    assertThat(ORDERED, is(succeededBy(FINISHED)));
  }
 
  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  finishedCannotProceedIntoOtherState() {
    FINISHED.next();
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  cancelledCannotProceedIntoOtherState() {
    CANCELLED.next();
  }

  private Matcher<OrderState> succeededBy(final OrderState state) {
    return new TypeSafeMatcher<OrderState>() {

      @Override
      public void describeTo(Description description) {
        description.appendText("succeeded by " + state);
      }

      @Override
      public boolean matchesSafely(OrderState initialState) {
        return initialState.next() == state;
      }
    };
  }

}