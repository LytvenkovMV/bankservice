CREATE FUNCTION calc_new_balance(add_percent int, max_percent int, init_bal numeric, curr_bal numeric)
    RETURNS numeric
    LANGUAGE plpgsql
AS
$$
DECLARE
    new_bal    numeric;
    max_bal    numeric;
    result_bal numeric;
BEGIN
    new_bal = curr_bal + curr_bal * add_percent / 100;
    max_bal = init_bal * max_percent / 100;
    IF new_bal < max_bal
    THEN
        result_bal = new_bal;
    ELSE
        result_bal = max_bal;
    END IF;
    RETURN result_bal;
END;
$$;