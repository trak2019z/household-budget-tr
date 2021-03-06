package rwilk.hb.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rwilk.hb.model.Category;
import rwilk.hb.model.CategorySpending;
import rwilk.hb.model.MonthSpending;
import rwilk.hb.model.Spend;
import rwilk.hb.model.User;
import rwilk.hb.repository.CategoryRepository;
import rwilk.hb.repository.SpendingRepository;
import rwilk.hb.repository.UserRepository;

@Service
public class SpendingServiceImpl implements SpendingService {

  @Autowired
  private SpendingRepository spendingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<Spend> getUserSpending(String username) {
    return spendingRepository.findAllByUserIsNullOrUser_Username(username);
  }

  @Override
  public Spend addSpend(Spend spend) {
    Optional<User> userOptional = userRepository.findByUsername(spend.getUser().getUsername());
    Optional<Category> categoryOptional = categoryRepository.findById(spend.getCategory().getId());
    if (userOptional.isPresent() && categoryOptional.isPresent()) {
      spend.setUser(userOptional.get());
      spend.setCategory(categoryOptional.get());
      return spendingRepository.save(spend);
    }
    return null;
  }

  @Override
  public Spend updateSpend(Spend spend) {
    Optional<User> userOptional = userRepository.findByUsername(spend.getUser().getUsername());
    Optional<Category> categoryOptional = categoryRepository.findById(spend.getCategory().getId());
    Optional<Spend> spendOptional = spendingRepository.findById(spend.getId());
    if (userOptional.isPresent() && categoryOptional.isPresent() && spendOptional.isPresent()) {
      spendOptional.get().setCategory(categoryOptional.get());
      spendOptional.get().setName(spend.getName());
      spendOptional.get().setDate(spend.getDate());
      spendOptional.get().setValue(spend.getValue());
      return spendingRepository.save(spendOptional.get());
    }
    return null;
  }

  @Override
  public void deleteSpend(Long id) {
    Optional<Spend> spendOptional = spendingRepository.findById(id);
    spendOptional.ifPresent(spend -> spendingRepository.delete(spend));
  }

  @Override
  public List<CategorySpending> getUserSpendingFromCurrentMonth(String username) {
    Calendar firstDay = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    firstDay.set(Calendar.DATE, 1);
    firstDay.set(Calendar.HOUR, 0);
    firstDay.set(Calendar.MINUTE, 0);
    firstDay.set(Calendar.SECOND, 0);
    firstDay.set(Calendar.MILLISECOND, 0);
    YearMonth yearMonth = YearMonth.now();
    Calendar lastDay = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    lastDay.set(Calendar.DATE, yearMonth.lengthOfMonth());
    lastDay.set(Calendar.HOUR, 0);
    lastDay.set(Calendar.MINUTE, 0);
    lastDay.set(Calendar.SECOND, 0);
    lastDay.set(Calendar.MILLISECOND, 0);
    List<Object> objects =
        spendingRepository.findAllByDateIsBetweenAndUser_UsernameAndGroupByCategory(firstDay, lastDay, username);
    List<CategorySpending> categorySpending = new ArrayList<>();
    for (Object o : objects) {
      Object[] obj = (Object[]) o;
      categorySpending.add(CategorySpending.builder()
          .name(String.valueOf(obj[0]))
          .sum(Double.valueOf(String.valueOf(obj[1])))
          .build());
    }
    return categorySpending;
  }

  @Override
  public List<CategorySpending> getUserSpendingFromLastMonth(String username) {
    Locale.setDefault(new Locale("en_GB"));
    Calendar firstDay = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    firstDay.set(Calendar.DATE, 1);
    firstDay.add(Calendar.MONTH, -1);
    firstDay.set(Calendar.HOUR, 0);
    firstDay.set(Calendar.MINUTE, 0);
    firstDay.set(Calendar.SECOND, 0);
    firstDay.set(Calendar.MILLISECOND, 0);

    YearMonth yearMonth = YearMonth.of(firstDay.get(Calendar.YEAR), firstDay.get(Calendar.MONTH)+1);
    Calendar lastDay = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    lastDay.add(Calendar.MONTH, -1);
    lastDay.set(Calendar.DATE, yearMonth.lengthOfMonth());
    lastDay.set(Calendar.HOUR, 0);
    lastDay.set(Calendar.MINUTE, 0);
    lastDay.set(Calendar.SECOND, 0);
    lastDay.set(Calendar.MILLISECOND, 0);

    List<Object> objects =
        spendingRepository.findAllByDateIsBetweenAndUser_UsernameAndGroupByCategory(firstDay, lastDay, username);
    List<CategorySpending> categorySpending = new ArrayList<>();
    for (Object o : objects) {
      Object[] obj = (Object[]) o;
      categorySpending.add(CategorySpending.builder()
          .name(String.valueOf(obj[0]))
          .sum(Double.valueOf(String.valueOf(obj[1])))
          .build());
    }
    return categorySpending;
  }

  @Override
  public List<MonthSpending> getLastYearSpending(String username) {
    List<Object> objects = spendingRepository.findAllSpending(username);
    List<MonthSpending> monthSpending = new ArrayList<>();
    for (Object o : objects) {
      Object[] obj = (Object[]) o;
      monthSpending.add(MonthSpending.builder()
          .month(Integer.valueOf(String.valueOf(obj[0])))
          .year(Double.valueOf(String.valueOf(obj[1])).intValue())
          .sum(Double.valueOf(String.valueOf(obj[2])))
          .build());
    }
    return monthSpending;
  }
}
